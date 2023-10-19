/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ScoreUpdateComponent from '@/entities/score/score-update.vue';
import ScoreClass from '@/entities/score/score-update.component';
import ScoreService from '@/entities/score/score.service';

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
  describe('Score Management Update Component', () => {
    let wrapper: Wrapper<ScoreClass>;
    let comp: ScoreClass;
    let scoreServiceStub: SinonStubbedInstance<ScoreService>;

    beforeEach(() => {
      scoreServiceStub = sinon.createStubInstance<ScoreService>(ScoreService);

      wrapper = shallowMount<ScoreClass>(ScoreUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          scoreService: () => scoreServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.score = entity;
        scoreServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(scoreServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.score = entity;
        scoreServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(scoreServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundScore = { id: 123 };
        scoreServiceStub.find.resolves(foundScore);
        scoreServiceStub.retrieve.resolves([foundScore]);

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
