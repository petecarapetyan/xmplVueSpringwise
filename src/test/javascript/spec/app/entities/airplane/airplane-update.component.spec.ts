/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AirplaneUpdateComponent from '@/entities/airplane/airplane-update.vue';
import AirplaneClass from '@/entities/airplane/airplane-update.component';
import AirplaneService from '@/entities/airplane/airplane.service';

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
  describe('Airplane Management Update Component', () => {
    let wrapper: Wrapper<AirplaneClass>;
    let comp: AirplaneClass;
    let airplaneServiceStub: SinonStubbedInstance<AirplaneService>;

    beforeEach(() => {
      airplaneServiceStub = sinon.createStubInstance<AirplaneService>(AirplaneService);

      wrapper = shallowMount<AirplaneClass>(AirplaneUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          airplaneService: () => airplaneServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.airplane = entity;
        airplaneServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(airplaneServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.airplane = entity;
        airplaneServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(airplaneServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAirplane = { id: 123 };
        airplaneServiceStub.find.resolves(foundAirplane);
        airplaneServiceStub.retrieve.resolves([foundAirplane]);

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
