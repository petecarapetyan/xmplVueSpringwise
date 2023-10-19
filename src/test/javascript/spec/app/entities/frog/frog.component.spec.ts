/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import FrogComponent from '@/entities/frog/frog.vue';
import FrogClass from '@/entities/frog/frog.component';
import FrogService from '@/entities/frog/frog.service';
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
  describe('Frog Management Component', () => {
    let wrapper: Wrapper<FrogClass>;
    let comp: FrogClass;
    let frogServiceStub: SinonStubbedInstance<FrogService>;

    beforeEach(() => {
      frogServiceStub = sinon.createStubInstance<FrogService>(FrogService);
      frogServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<FrogClass>(FrogComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          frogService: () => frogServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      frogServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllFrogs();
      await comp.$nextTick();

      // THEN
      expect(frogServiceStub.retrieve.called).toBeTruthy();
      expect(comp.frogs[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      frogServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(frogServiceStub.retrieve.callCount).toEqual(1);

      comp.removeFrog();
      await comp.$nextTick();

      // THEN
      expect(frogServiceStub.delete.called).toBeTruthy();
      expect(frogServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
