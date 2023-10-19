/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CarDetailComponent from '@/entities/car/car-details.vue';
import CarClass from '@/entities/car/car-details.component';
import CarService from '@/entities/car/car.service';
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
  describe('Car Management Detail Component', () => {
    let wrapper: Wrapper<CarClass>;
    let comp: CarClass;
    let carServiceStub: SinonStubbedInstance<CarService>;

    beforeEach(() => {
      carServiceStub = sinon.createStubInstance<CarService>(CarService);

      wrapper = shallowMount<CarClass>(CarDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { carService: () => carServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCar = { id: 123 };
        carServiceStub.find.resolves(foundCar);

        // WHEN
        comp.retrieveCar(123);
        await comp.$nextTick();

        // THEN
        expect(comp.car).toBe(foundCar);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCar = { id: 123 };
        carServiceStub.find.resolves(foundCar);

        // WHEN
        comp.beforeRouteEnter({ params: { carId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.car).toBe(foundCar);
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
