import Vue from 'vue';
import Router from "vue-router";
import Login from './views/Login';
import Admin from './views/Admin';
import Welcome from './views/admin/Welcome';
import Chapter from './views/admin/Chapter';
import Section from "./views/admin/Section";
import Course from "./views/admin/Course";
import Category from "./views/admin/Category";
import Teacher from "./views/admin/Teacher";
import File from "./views/admin/File";
import Content from "./views/admin/Content";
import User from "./views/admin/User";
import Resource from "./views/admin/Resource";
import Role from "./views/admin/Role";


Vue.use(Router);

export default new Router({
    mode:'history',
    base:process.env.BASE_URL,
    routes:[
        {
            path:'*',
            redirect:'/login',
        },
        {
            path: '/login',
            component:Login
        },
        {
            path: '/',
            name:'admin',
            component:Admin,
            meta:{
                loginRequire:true
            },
            children:[
                {
                    path: 'welcome',
                    name:'welcome',
                    component:Welcome
                },
                {
                    path: 'system/user',
                    name:'system/user',
                    component:User
                },
                {
                    path: 'system/resource',
                    name:'system/resource',
                    component:Resource
                },
                {
                    path: '/system/role',
                    name:'system/role',
                    component:Role
                },

                {
                    path: 'business/category',
                    name:'business/category',
                    component:Category
                },
                {
                    path: 'business/teacher',
                    name:'business/teacher',
                    component:Teacher
                },
                {
                    path: 'business/course',
                    name:'business/course',
                    component:Course
                },
                {
                    path: 'business/chapter',
                    name:'business/chapter',
                    component:Chapter
                },
                {
                    path: 'business/section',
                    name:'business/section',
                    component:Section
                },
                {
                    path: 'business/content',
                    name:'business/content',
                    component:Content
                },
                {
                    path: 'file/file',
                    name:'file/file',
                    component:File
                }
            ]
        }
    ]
})
