/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ScoreDetailComponent from '@/entities/score/score-details.vue';
import ScoreClass from '@/entities/score/score-details.component';
import ScoreService from '@/entities/score/score.service';
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
  describe('Score Management Detail Component', () => {
    let wrapper: Wrapper<ScoreClass>;
    let comp: ScoreClass;
    let scoreServiceStub: SinonStubbedInstance<ScoreService>;

    beforeEach(() => {
      scoreServiceStub = sinon.createStubInstance<ScoreService>(ScoreService);

      wrapper = shallowMount<ScoreClass>(ScoreDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { scoreService: () => scoreServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundScore = { id: 123 };
        scoreServiceStub.find.resolves(foundScore);

        // WHEN
        comp.retrieveScore(123);
        await comp.$nextTick();

        // THEN
        expect(comp.score).toBe(foundScore);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundScore = { id: 123 };
        scoreServiceStub.find.resolves(foundScore);

        // WHEN
        comp.beforeRouteEnter({ params: { scoreId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.score).toBe(foundScore);
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
