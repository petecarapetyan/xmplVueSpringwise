/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SpringProjectComponent from '@/entities/spring-project/spring-project.vue';
import SpringProjectClass from '@/entities/spring-project/spring-project.component';
import SpringProjectService from '@/entities/spring-project/spring-project.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('SpringProject Management Component', () => {
    let wrapper: Wrapper<SpringProjectClass>;
    let comp: SpringProjectClass;
    let springProjectServiceStub: SinonStubbedInstance<SpringProjectService>;

    beforeEach(() => {
      springProjectServiceStub = sinon.createStubInstance<SpringProjectService>(SpringProjectService);
      springProjectServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SpringProjectClass>(SpringProjectComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          springProjectService: () => springProjectServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      springProjectServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSpringProjects();
      await comp.$nextTick();

      // THEN
      expect(springProjectServiceStub.retrieve.called).toBeTruthy();
      expect(comp.springProjects[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      springProjectServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(springProjectServiceStub.retrieve.callCount).toEqual(1);

      comp.removeSpringProject();
      await comp.$nextTick();

      // THEN
      expect(springProjectServiceStub.delete.called).toBeTruthy();
      expect(springProjectServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
