import axios from 'axios'
import qs from 'qs'
axios.defaults.baseURL = 'http://154.209.88.21:8080/blog/api';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
axios.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
if (window.sessionStorage.getItem('token'))
    axios.interceptors.request.use(config => {
        config.headers.Token = window.sessionStorage.getItem('token');
        return config;
    });
export default {
    axios: axios,
    login: (username, password) => axios.post('/login', qs.stringify({ username, password })),
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
    getAboutPage: () => axios.get('/about'),
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
    addCatagory: function (name) {
        let data = {
            name
        };
        return axios.post('/catagory', qs.stringify(data))
    },
    updateCatagory: function (id, name) {
        let data = {
            id,
            name
        }
        return axios.put('/catagory', qs.stringify(data))
    },
    deleteCatagory: id => axios.delete('/catagory', {
        params: {
            id
        }
    }),
    addArticle: (title, content, catagoryId, tags, author, cover) => {
        let data = {
            title,
            content,
            catagoryId,
            tags,
            author,
            cover
        };
        return axios.post('/articles', qs.stringify(data))
    },
    updateArticle: (id, title, content, catagoryId, tags, author, cover) => {
        let data = {
            id,
            title,
            content,
            catagoryId,
            tags,
            author,
            cover
        };
        return axios.put('/articles', qs.stringify(data))
    },
    deleteArticle: id => axios.delete('/articles?id=' + id),
    getNewComments: (limit, page) => axios.get(`/comments?limit=${limit}&page=${page}`),
    deleteComment: id => axios.delete('/comments?id=' + id),
    updateSiteInfo: (name, title, image, introduction, about) => axios.put('/info', qs.stringify({ name, title, image, introduction, about }))
}