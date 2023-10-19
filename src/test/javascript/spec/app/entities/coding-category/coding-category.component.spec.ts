/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CodingCategoryComponent from '@/entities/coding-category/coding-category.vue';
import CodingCategoryClass from '@/entities/coding-category/coding-category.component';
import CodingCategoryService from '@/entities/coding-category/coding-category.service';
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
  describe('CodingCategory Management Component', () => {
    let wrapper: Wrapper<CodingCategoryClass>;
    let comp: CodingCategoryClass;
    let codingCategoryServiceStub: SinonStubbedInstance<CodingCategoryService>;

    beforeEach(() => {
      codingCategoryServiceStub = sinon.createStubInstance<CodingCategoryService>(CodingCategoryService);
      codingCategoryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CodingCategoryClass>(CodingCategoryComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          codingCategoryService: () => codingCategoryServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      codingCategoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCodingCategorys();
      await comp.$nextTick();

      // THEN
      expect(codingCategoryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.codingCategories[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      codingCategoryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(codingCategoryServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCodingCategory();
      await comp.$nextTick();

      // THEN
      expect(codingCategoryServiceStub.delete.called).toBeTruthy();
      expect(codingCategoryServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
