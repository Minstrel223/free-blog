package cn.sakurablossom.freeblog;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/blog/api")
@CrossOrigin(origins = "*")
public class MainController {
    @Autowired
    private UserReposity userReposity;
    @Autowired
    private ArticleReposity articleReposity;
    @Autowired
    private CatagoryReposity catagoryReposity;
    @Autowired
    private TagReposity tagReposity;
    @Autowired
    private CommentReposity commentReposity;
    @Autowired
    private SiteReposity siteReposity;

    private String token;

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMail(String to, String subject, String content) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("abcgpsa@163.com");
        message.setTo(to); // 邮件接受者
        message.setSubject(subject); // 主题
        message.setText(content); // 内容

        mailSender.send(message);
    }

    @PostMapping(path = "/login")
    public MyResponse login(@RequestParam String username, @RequestParam String password) {
        if (userReposity.findAll() == null || !userReposity.findAll().iterator().hasNext()) {// 初次访问，进行注册
            User n = new User();
            n.setUser_id(1);
            n.setUser_name(username);
            n.setUser_password(password);
            userReposity.save(n);
            this.token = Math.random() + "";

            MyResponse res = new MyResponse("success", this.token, 1);
            return res;
        } else {
            User user = userReposity.findById(1).get();
            if (user.getUser_name().equals(username) && user.getUser_password().equals(password)) {
                this.token = Math.random() + "";
                MyResponse res = new MyResponse("success", this.token, 1);
                return res;
            } else {
                MyResponse res = new MyResponse("login fail: 用户名或密码错误", null, 1);
                return res;
            }
        }
    }

    @PostMapping(path = "/articles")
    public MyResponse addArticle(@RequestHeader("Token") String token, @RequestParam String title,
            @RequestParam String author, @RequestParam String content, @RequestParam(required = false) String cover,
            @RequestParam(required = false) Integer catagoryId, @RequestParam(required = false) String[] tags) {

        if (this.token == null || !this.token.equals(token)) {
            MyResponse res = new MyResponse("error: wrong token", null, 1);
            return res;
        }

        Article article = new Article();
        article.setArticle_title(title);
        article.setArticle_author(author);
        article.setArticle_content(content);
        article.setArticle_cover(cover);
        article.setCatagory_id(catagoryId);
        article.setArticle_views(0);
        article.setComments_number(0);
        articleReposity.save(article);
        if (tags != null) {
            for (int i = 0; i < tags.length; i++) {
                Tag tag = new Tag();
                tag.setTag_name(tags[i]);
                tag.setArticle_id(article.getArticle_id());
                tagReposity.save(tag);
            }
        }

        MyResponse res = new MyResponse("success", null, 1);
        return res;
    }

    @PutMapping(path = "/articles")
    public MyResponse updateArticle(@RequestHeader("Token") String token, @RequestParam Integer id,
            @RequestParam String title, @RequestParam String author, @RequestParam String content,
            @RequestParam(required = false) String cover, @RequestParam(required = false) Integer catagoryId,
            @RequestParam(required = false) String[] tags) {

        if (this.token == null || !this.token.equals(token)) {
            MyResponse res = new MyResponse("error: wrong token", null, 1);
            return res;
        }

        Article article = articleReposity.findById(id).get();
        article.setArticle_title(title);
        article.setArticle_author(author);
        article.setArticle_content(content);
        article.setArticle_cover(cover);
        article.setCatagory_id(catagoryId);
        article.setArticle_views(0);
        article.setComments_number(0);
        articleReposity.save(article);
        Iterator<Tag> itor = tagReposity.findByArticleId(id).iterator();
        while (itor.hasNext()) {
            tagReposity.delete(itor.next());
        }

        if (tags != null) {
            for (int i = 0; i < tags.length; i++) {
                Tag tag = new Tag();
                tag.setTag_name(tags[i]);
                tag.setArticle_id(article.getArticle_id());
                tagReposity.save(tag);
            }
        }

        MyResponse res = new MyResponse("success", null, 1);
        return res;
    }

    @DeleteMapping(path = "/articles")
    public MyResponse deleteArticle(@RequestHeader("Token") String token, @RequestParam(required = true) Integer id) {
        if (this.token == null || !this.token.equals(token)) {
            MyResponse res = new MyResponse("error: wrong token", null, 1);
            return res;
        }
        articleReposity.deleteById(id);
        Iterator<Tag> itor = tagReposity.findByArticleId(id).iterator();
        while (itor.hasNext()) {
            tagReposity.delete(itor.next());
        }
        Iterable<Comment> comments = commentReposity.findbyArticleId(id);
        commentReposity.deleteAll(comments);
        return new MyResponse("success", null, 1);
    }

    @GetMapping(path = "/articles")
    public MyResponse getNewArticles(@RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer catagory_id,
            @RequestParam(required = false) Integer article_id) {
        if (article_id != null) {
            Article article = articleReposity.findById(article_id).get();
            return new MyResponse("success", article, 1);
        } else if (catagory_id != null) {
            Iterable<Article> list = articleReposity.findByCatagoryId(catagory_id);
            return new MyResponse("success", list, 1);
        } else {
            if (page == null)
                page = 1;
            if (limit == null)
                limit = 8;
            int size = (int) articleReposity.count();
            int pages = (size - 1) / limit + 1;
            Iterable<Article> list = articleReposity.getSection((page - 1) * limit, limit);
            return new MyResponse("success", list, pages);
        }
    }

    @PutMapping(path = "/articles/views")
    public MyResponse increaseViews(@RequestParam(required = true) Integer article_id) {
        Article article = articleReposity.findById(article_id).get();
        article.setArticle_views(article.getArticle_views() + 1);
        articleReposity.save(article);
        return new MyResponse("success", null, 1);
    }

    @PostMapping(path = "/catagory")
    public MyResponse addCatagory(@RequestHeader("Token") String token, @RequestParam(required = true) String name) {
        if (this.token == null || !this.token.equals(token)) {
            MyResponse res = new MyResponse("error: wrong token", null, 1);
            return res;
        }
        Catagory catagory = new Catagory();
        catagory.setCatagory_name(name);
        catagoryReposity.save(catagory);
        return new MyResponse("success", null, 1);
    }

    @DeleteMapping(path = "/catagory")
    public MyResponse deleteCatagory(@RequestHeader("Token") String token, @RequestParam(required = true) Integer id) {
        if (this.token == null || !this.token.equals(token)) {
            MyResponse res = new MyResponse("error: wrong token", null, 1);
            return res;
        }
        catagoryReposity.deleteById(id);
        return new MyResponse("success", null, 1);
    }

    @PutMapping(path = "/catagory")
    public MyResponse updateCatagory(@RequestHeader("Token") String token, @RequestParam(required = true) Integer id,
            @RequestParam(required = true) String name) {
        if (this.token == null || !this.token.equals(token)) {
            MyResponse res = new MyResponse("error: wrong token", null, 1);
            return res;
        }
        Catagory catagory = catagoryReposity.findById(id).get();
        catagory.setCatagory_name(name);
        catagoryReposity.save(catagory);
        return new MyResponse("success", null, 1);
    }

    @GetMapping(path = "/catagory")
    public MyResponse getCatalog() {
        Iterable<Catagory> catalog = catagoryReposity.findAll();
        return new MyResponse("success", catalog, 1);
    }

    @GetMapping(path = "/tags")
    public MyResponse getTags(@RequestParam(required = true) Integer article_id) {
        Iterable<Tag> list = tagReposity.findByArticleId(article_id);
        ArrayList<String> tags = new ArrayList<String>();
        Iterator<Tag> iterator = list.iterator();
        while (iterator.hasNext()) {
            tags.add(iterator.next().getTag_name());
        }
        return new MyResponse("success", tags, 1);
    }

    @PostMapping(path = "/comments")
    public MyResponse addComment(@RequestParam(required = true) String name,
            @RequestParam(required = true) String email, @RequestParam(required = true) String content,
            @RequestParam(required = true) Integer article_id,
            @RequestParam(required = false) Integer target_comment_id) {
        Comment comment = new Comment();
        comment.setCommentor_name(name);
        comment.setCommentor_email(email);
        comment.setComment_content(content);
        comment.setArticle_id(article_id);
        comment.setParent_comment_id(target_comment_id);
        commentReposity.save(comment);
        Article article = articleReposity.findById(article_id).get();
        article.setComments_number(article.getComments_number() + 1);
        articleReposity.save(article);
        if (target_comment_id != null) {
            Comment to = commentReposity.findById(target_comment_id).get();
            String mailContent = "您好," + to.getCommentor_name() + "。" + name + "回复您：" + content;
            try {
                this.sendSimpleMail(to.getCommentor_email(), "您收到了一条新回复-freeblog", mailContent);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            try {
                this.sendSimpleMail("eulerequation@126.com", "新回复", name + ": " + content);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return new MyResponse("success", null, 1);
    }

    @DeleteMapping(path = "/comments")
    public MyResponse deleteComment(@RequestHeader("Token") String token, @RequestParam(required = true) Integer id) {
        if (this.token == null || !this.token.equals(token)) {
            MyResponse res = new MyResponse("error: wrong token", null, 1);
            return res;
        }
        Comment cmt = commentReposity.findById(id).get();
        Article article = articleReposity.findById(cmt.getArticle_id()).get();
        article.setComments_number(article.getComments_number() - 1);
        articleReposity.save(article);
        commentReposity.delete(cmt);
        return new MyResponse("success", null, 1);
    }

    @GetMapping(path = "/comments")
    public MyResponse getComments(@RequestParam(required = false) Integer comment_id,
            @RequestParam(required = false) Integer article_id, @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer page) {
        if (comment_id != null) {
            Comment comment = commentReposity.findById(comment_id).get();
            return new MyResponse("success", comment, 1);
        } else if (article_id != null) {
            Iterable<Comment> list = commentReposity.findbyArticleId(article_id);
            return new MyResponse("success", list, 1);
        } else {
            if (limit == null)
                limit = 8;
            if (page == null)
                page = 1;
            int size = (int) commentReposity.count();
            int pages = (size - 1) / limit + 1;
            Iterable<Comment> list = commentReposity.getSection((page - 1) * limit, limit);
            return new MyResponse("success", list, pages);
        }
    }

    @PutMapping(path = "/info")
    public MyResponse updateSiteInfo(@RequestHeader("Token") String token, @RequestParam String name,
            @RequestParam String title, @RequestParam String introduction, @RequestParam String image,
            @RequestParam String about) {
        if (this.token == null || !this.token.equals(token)) {
            MyResponse res = new MyResponse("error: wrong token", null, 1);
            return res;
        }
        Site site = null;
        if (!siteReposity.existsById(1)) {
            site = new Site();
        } else {
            site = siteReposity.findById(1).get();
        }
        site.setSite_name(name);
        site.setSite_title(title);
        site.setSite_introduction(introduction);
        site.setImage_url(image);
        site.setAbout_page(about);
        siteReposity.save(site);
        return new MyResponse("success", null, 1);
    }

    @GetMapping(path = "/info")
    public MyResponse getSiteInfo() {
        Site site = siteReposity.findById(1).get();
        return new MyResponse("success", site, 1);
    }

    @GetMapping(path = "/search")
    public MyResponse searchArticle(@RequestParam(required = true) String sq) {
        Iterable<Article> iterable = articleReposity.Search("%" + sq + "%");
        return new MyResponse("success", iterable, 1);
    }

}