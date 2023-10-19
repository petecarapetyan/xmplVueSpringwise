/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ScoreTypeDetailComponent from '@/entities/score-type/score-type-details.vue';
import ScoreTypeClass from '@/entities/score-type/score-type-details.component';
import ScoreTypeService from '@/entities/score-type/score-type.service';
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
  describe('ScoreType Management Detail Component', () => {
    let wrapper: Wrapper<ScoreTypeClass>;
    let comp: ScoreTypeClass;
    let scoreTypeServiceStub: SinonStubbedInstance<ScoreTypeService>;

    beforeEach(() => {
      scoreTypeServiceStub = sinon.createStubInstance<ScoreTypeService>(ScoreTypeService);

      wrapper = shallowMount<ScoreTypeClass>(ScoreTypeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { scoreTypeService: () => scoreTypeServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundScoreType = { id: 123 };
        scoreTypeServiceStub.find.resolves(foundScoreType);

        // WHEN
        comp.retrieveScoreType(123);
        await comp.$nextTick();

        // THEN
        expect(comp.scoreType).toBe(foundScoreType);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundScoreType = { id: 123 };
        scoreTypeServiceStub.find.resolves(foundScoreType);

        // WHEN
        comp.beforeRouteEnter({ params: { scoreTypeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.scoreType).toBe(foundScoreType);
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
