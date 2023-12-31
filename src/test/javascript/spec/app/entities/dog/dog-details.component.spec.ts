/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DogDetailComponent from '@/entities/dog/dog-details.vue';
import DogClass from '@/entities/dog/dog-details.component';
import DogService from '@/entities/dog/dog.service';
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
  describe('Dog Management Detail Component', () => {
    let wrapper: Wrapper<DogClass>;
    let comp: DogClass;
    let dogServiceStub: SinonStubbedInstance<DogService>;

    beforeEach(() => {
      dogServiceStub = sinon.createStubInstance<DogService>(DogService);

      wrapper = shallowMount<DogClass>(DogDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { dogService: () => dogServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDog = { id: 123 };
        dogServiceStub.find.resolves(foundDog);

        // WHEN
        comp.retrieveDog(123);
        await comp.$nextTick();

        // THEN
        expect(comp.dog).toBe(foundDog);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDog = { id: 123 };
        dogServiceStub.find.resolves(foundDog);

        // WHEN
        comp.beforeRouteEnter({ params: { dogId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.dog).toBe(foundDog);
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
