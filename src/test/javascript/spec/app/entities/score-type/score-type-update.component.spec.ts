/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ScoreTypeUpdateComponent from '@/entities/score-type/score-type-update.vue';
import ScoreTypeClass from '@/entities/score-type/score-type-update.component';
import ScoreTypeService from '@/entities/score-type/score-type.service';

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
  describe('ScoreType Management Update Component', () => {
    let wrapper: Wrapper<ScoreTypeClass>;
    let comp: ScoreTypeClass;
    let scoreTypeServiceStub: SinonStubbedInstance<ScoreTypeService>;

    beforeEach(() => {
      scoreTypeServiceStub = sinon.createStubInstance<ScoreTypeService>(ScoreTypeService);

      wrapper = shallowMount<ScoreTypeClass>(ScoreTypeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          scoreTypeService: () => scoreTypeServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.scoreType = entity;
        scoreTypeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(scoreTypeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.scoreType = entity;
        scoreTypeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(scoreTypeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundScoreType = { id: 123 };
        scoreTypeServiceStub.find.resolves(foundScoreType);
        scoreTypeServiceStub.retrieve.resolves([foundScoreType]);

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
