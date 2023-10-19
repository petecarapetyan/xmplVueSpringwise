/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AirplaneDetailComponent from '@/entities/airplane/airplane-details.vue';
import AirplaneClass from '@/entities/airplane/airplane-details.component';
import AirplaneService from '@/entities/airplane/airplane.service';
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
  describe('Airplane Management Detail Component', () => {
    let wrapper: Wrapper<AirplaneClass>;
    let comp: AirplaneClass;
    let airplaneServiceStub: SinonStubbedInstance<AirplaneService>;

    beforeEach(() => {
      airplaneServiceStub = sinon.createStubInstance<AirplaneService>(AirplaneService);

      wrapper = shallowMount<AirplaneClass>(AirplaneDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { airplaneService: () => airplaneServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAirplane = { id: 123 };
        airplaneServiceStub.find.resolves(foundAirplane);

        // WHEN
        comp.retrieveAirplane(123);
        await comp.$nextTick();

        // THEN
        expect(comp.airplane).toBe(foundAirplane);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAirplane = { id: 123 };
        airplaneServiceStub.find.resolves(foundAirplane);

        // WHEN
        comp.beforeRouteEnter({ params: { airplaneId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.airplane).toBe(foundAirplane);
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
