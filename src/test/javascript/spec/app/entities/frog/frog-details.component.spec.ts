/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FrogDetailComponent from '@/entities/frog/frog-details.vue';
import FrogClass from '@/entities/frog/frog-details.component';
import FrogService from '@/entities/frog/frog.service';
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
  describe('Frog Management Detail Component', () => {
    let wrapper: Wrapper<FrogClass>;
    let comp: FrogClass;
    let frogServiceStub: SinonStubbedInstance<FrogService>;

    beforeEach(() => {
      frogServiceStub = sinon.createStubInstance<FrogService>(FrogService);

      wrapper = shallowMount<FrogClass>(FrogDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { frogService: () => frogServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFrog = { id: 123 };
        frogServiceStub.find.resolves(foundFrog);

        // WHEN
        comp.retrieveFrog(123);
        await comp.$nextTick();

        // THEN
        expect(comp.frog).toBe(foundFrog);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFrog = { id: 123 };
        frogServiceStub.find.resolves(foundFrog);

        // WHEN
        comp.beforeRouteEnter({ params: { frogId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.frog).toBe(foundFrog);
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
