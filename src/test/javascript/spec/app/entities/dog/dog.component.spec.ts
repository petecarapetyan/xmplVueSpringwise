/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DogComponent from '@/entities/dog/dog.vue';
import DogClass from '@/entities/dog/dog.component';
import DogService from '@/entities/dog/dog.service';
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
  describe('Dog Management Component', () => {
    let wrapper: Wrapper<DogClass>;
    let comp: DogClass;
    let dogServiceStub: SinonStubbedInstance<DogService>;

    beforeEach(() => {
      dogServiceStub = sinon.createStubInstance<DogService>(DogService);
      dogServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DogClass>(DogComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          dogService: () => dogServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      dogServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDogs();
      await comp.$nextTick();

      // THEN
      expect(dogServiceStub.retrieve.called).toBeTruthy();
      expect(comp.dogs[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      dogServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(dogServiceStub.retrieve.callCount).toEqual(1);

      comp.removeDog();
      await comp.$nextTick();

      // THEN
      expect(dogServiceStub.delete.called).toBeTruthy();
      expect(dogServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
