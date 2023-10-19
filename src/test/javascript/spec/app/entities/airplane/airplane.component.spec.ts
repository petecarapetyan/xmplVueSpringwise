/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AirplaneComponent from '@/entities/airplane/airplane.vue';
import AirplaneClass from '@/entities/airplane/airplane.component';
import AirplaneService from '@/entities/airplane/airplane.service';
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
  describe('Airplane Management Component', () => {
    let wrapper: Wrapper<AirplaneClass>;
    let comp: AirplaneClass;
    let airplaneServiceStub: SinonStubbedInstance<AirplaneService>;

    beforeEach(() => {
      airplaneServiceStub = sinon.createStubInstance<AirplaneService>(AirplaneService);
      airplaneServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AirplaneClass>(AirplaneComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          airplaneService: () => airplaneServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      airplaneServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAirplanes();
      await comp.$nextTick();

      // THEN
      expect(airplaneServiceStub.retrieve.called).toBeTruthy();
      expect(comp.airplanes[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      airplaneServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(airplaneServiceStub.retrieve.callCount).toEqual(1);

      comp.removeAirplane();
      await comp.$nextTick();

      // THEN
      expect(airplaneServiceStub.delete.called).toBeTruthy();
      expect(airplaneServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
