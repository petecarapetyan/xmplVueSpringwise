/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TruckComponent from '@/entities/truck/truck.vue';
import TruckClass from '@/entities/truck/truck.component';
import TruckService from '@/entities/truck/truck.service';
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
  describe('Truck Management Component', () => {
    let wrapper: Wrapper<TruckClass>;
    let comp: TruckClass;
    let truckServiceStub: SinonStubbedInstance<TruckService>;

    beforeEach(() => {
      truckServiceStub = sinon.createStubInstance<TruckService>(TruckService);
      truckServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TruckClass>(TruckComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          truckService: () => truckServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      truckServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTrucks();
      await comp.$nextTick();

      // THEN
      expect(truckServiceStub.retrieve.called).toBeTruthy();
      expect(comp.trucks[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      truckServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(truckServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTruck();
      await comp.$nextTick();

      // THEN
      expect(truckServiceStub.delete.called).toBeTruthy();
      expect(truckServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
