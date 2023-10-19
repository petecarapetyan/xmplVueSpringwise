/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SpringProjectDetailComponent from '@/entities/spring-project/spring-project-details.vue';
import SpringProjectClass from '@/entities/spring-project/spring-project-details.component';
import SpringProjectService from '@/entities/spring-project/spring-project.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('SpringProject Management Detail Component', () => {
    let wrapper: Wrapper<SpringProjectClass>;
    let comp: SpringProjectClass;
    let springProjectServiceStub: SinonStubbedInstance<SpringProjectService>;

    beforeEach(() => {
      springProjectServiceStub = sinon.createStubInstance<SpringProjectService>(SpringProjectService);

      wrapper = shallowMount<SpringProjectClass>(SpringProjectDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { springProjectService: () => springProjectServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSpringProject = { id: 123 };
        springProjectServiceStub.find.resolves(foundSpringProject);

        // WHEN
        comp.retrieveSpringProject(123);
        await comp.$nextTick();

        // THEN
        expect(comp.springProject).toBe(foundSpringProject);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSpringProject = { id: 123 };
        springProjectServiceStub.find.resolves(foundSpringProject);

        // WHEN
        comp.beforeRouteEnter({ params: { springProjectId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.springProject).toBe(foundSpringProject);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
