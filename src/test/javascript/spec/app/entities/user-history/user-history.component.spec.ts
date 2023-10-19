/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import UserHistoryComponent from '@/entities/user-history/user-history.vue';
import UserHistoryClass from '@/entities/user-history/user-history.component';
import UserHistoryService from '@/entities/user-history/user-history.service';
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
  describe('UserHistory Management Component', () => {
    let wrapper: Wrapper<UserHistoryClass>;
    let comp: UserHistoryClass;
    let userHistoryServiceStub: SinonStubbedInstance<UserHistoryService>;

    beforeEach(() => {
      userHistoryServiceStub = sinon.createStubInstance<UserHistoryService>(UserHistoryService);
      userHistoryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<UserHistoryClass>(UserHistoryComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          userHistoryService: () => userHistoryServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      userHistoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllUserHistorys();
      await comp.$nextTick();

      // THEN
      expect(userHistoryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.userHistories[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      userHistoryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(userHistoryServiceStub.retrieve.callCount).toEqual(1);

      comp.removeUserHistory();
      await comp.$nextTick();

      // THEN
      expect(userHistoryServiceStub.delete.called).toBeTruthy();
      expect(userHistoryServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
