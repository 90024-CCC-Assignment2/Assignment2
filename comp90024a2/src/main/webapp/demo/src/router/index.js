import Vue from 'vue'
import Router from 'vue-router'
import View1 from '@/components/View1'
import View2 from '@/components/View2'
import View3 from '@/components/View3'
import Index from '@/components/Index'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/view1',
      name: 'View1',
      component: View1
    },
    {
      path: '/view2',
      name: 'View2',
      component: View2
    },
    {
      path: '/view3',
      name: 'View3',
      component: View3
    }
  ]
})
