/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SpringProjectUpdateComponent from '@/entities/spring-project/spring-project-update.vue';
import SpringProjectClass from '@/entities/spring-project/spring-project-update.component';
import SpringProjectService from '@/entities/spring-project/spring-project.service';

import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('SpringProject Management Update Component', () => {
    let wrapper: Wrapper<SpringProjectClass>;
    let comp: SpringProjectClass;
    let springProjectServiceStub: SinonStubbedInstance<SpringProjectService>;

    beforeEach(() => {
      springProjectServiceStub = sinon.createStubInstance<SpringProjectService>(SpringProjectService);

      wrapper = shallowMount<SpringProjectClass>(SpringProjectUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          springProjectService: () => springProjectServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.springProject = entity;
        springProjectServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(springProjectServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.springProject = entity;
        springProjectServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(springProjectServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSpringProject = { id: 123 };
        springProjectServiceStub.find.resolves(foundSpringProject);
        springProjectServiceStub.retrieve.resolves([foundSpringProject]);

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
