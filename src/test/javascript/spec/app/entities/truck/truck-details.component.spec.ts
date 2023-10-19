/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TruckDetailComponent from '@/entities/truck/truck-details.vue';
import TruckClass from '@/entities/truck/truck-details.component';
import TruckService from '@/entities/truck/truck.service';
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
  describe('Truck Management Detail Component', () => {
    let wrapper: Wrapper<TruckClass>;
    let comp: TruckClass;
    let truckServiceStub: SinonStubbedInstance<TruckService>;

    beforeEach(() => {
      truckServiceStub = sinon.createStubInstance<TruckService>(TruckService);

      wrapper = shallowMount<TruckClass>(TruckDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { truckService: () => truckServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTruck = { id: 123 };
        truckServiceStub.find.resolves(foundTruck);

        // WHEN
        comp.retrieveTruck(123);
        await comp.$nextTick();

        // THEN
        expect(comp.truck).toBe(foundTruck);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTruck = { id: 123 };
        truckServiceStub.find.resolves(foundTruck);

        // WHEN
        comp.beforeRouteEnter({ params: { truckId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.truck).toBe(foundTruck);
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
