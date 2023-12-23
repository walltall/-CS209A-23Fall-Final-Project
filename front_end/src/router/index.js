import Vue from 'vue'
import VueRouter from 'vue-router'
import Topic from '@/views/topic.vue'
import Bug from '@/views/bug.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        component: Topic
    },
    {
        path: '/topic',
        component: Topic
    },
    {
        path: '/bug',
        component: Bug
    },
    {
        path: '/phrase',
        component: () => import('@/views/phrase.vue')
    }
]

const router=new VueRouter({
    mode:'history',
    base:process.env.BASE_URL,
    routes
})

export default router