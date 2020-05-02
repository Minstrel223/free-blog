import axios from 'axios';
import qs from 'qs';
axios.defaults.baseURL = 'http://154.209.88.21:8080/blog/api';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default {
    getNewArticles: (limit, page) => axios.get('/articles', {
        params: {
            limit,
            page
        }
    }),
    getCatalog: () => axios.get('/catagory'),
    getComments: article_id => axios.get('/comments', {
        params: {
            article_id,
        }
    }),
    getCommentById: comment_id => axios.get('/comments', {
        params: {
            comment_id,
        }
    }),
    getArticlesByCatagoryId: catagory_id => axios.get('/articles', {
        params: {
            catagory_id,
        }
    }),
    submitComment: function (name, email, content, article_id, target_comment_id) {
        let data = {
            name,
            email,
            content,
            article_id,
            target_comment_id,
        };
        return axios.post('/comments', qs.stringify(data))
    },
    searchArticles: (sq) => axios.get('/search', {
        params: {
            sq,
        }
    }),
    getSiteInfo: () => axios.get('/info'),
    getTags: article_id => axios.get('/tags', {
        params: {
            article_id,
        }
    }),
    increaseView: (article_id) => axios.put('/articles/views', qs.stringify({ article_id }))
}