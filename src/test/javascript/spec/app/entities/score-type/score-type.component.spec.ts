/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ScoreTypeComponent from '@/entities/score-type/score-type.vue';
import ScoreTypeClass from '@/entities/score-type/score-type.component';
import ScoreTypeService from '@/entities/score-type/score-type.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('ScoreType Management Component', () => {
    let wrapper: Wrapper<ScoreTypeClass>;
    let comp: ScoreTypeClass;
    let scoreTypeServiceStub: SinonStubbedInstance<ScoreTypeService>;

    beforeEach(() => {
      scoreTypeServiceStub = sinon.createStubInstance<ScoreTypeService>(ScoreTypeService);
      scoreTypeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ScoreTypeClass>(ScoreTypeComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          scoreTypeService: () => scoreTypeServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      scoreTypeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllScoreTypes();
      await comp.$nextTick();

      // THEN
      expect(scoreTypeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.scoreTypes[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      scoreTypeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(scoreTypeServiceStub.retrieve.callCount).toEqual(1);

      comp.removeScoreType();
      await comp.$nextTick();

      // THEN
      expect(scoreTypeServiceStub.delete.called).toBeTruthy();
      expect(scoreTypeServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
