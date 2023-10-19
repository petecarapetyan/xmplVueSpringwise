/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import UserHistoryDetailComponent from '@/entities/user-history/user-history-details.vue';
import UserHistoryClass from '@/entities/user-history/user-history-details.component';
import UserHistoryService from '@/entities/user-history/user-history.service';
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
  describe('UserHistory Management Detail Component', () => {
    let wrapper: Wrapper<UserHistoryClass>;
    let comp: UserHistoryClass;
    let userHistoryServiceStub: SinonStubbedInstance<UserHistoryService>;

    beforeEach(() => {
      userHistoryServiceStub = sinon.createStubInstance<UserHistoryService>(UserHistoryService);

      wrapper = shallowMount<UserHistoryClass>(UserHistoryDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { userHistoryService: () => userHistoryServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUserHistory = { id: 123 };
        userHistoryServiceStub.find.resolves(foundUserHistory);

        // WHEN
        comp.retrieveUserHistory(123);
        await comp.$nextTick();

        // THEN
        expect(comp.userHistory).toBe(foundUserHistory);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUserHistory = { id: 123 };
        userHistoryServiceStub.find.resolves(foundUserHistory);

        // WHEN
        comp.beforeRouteEnter({ params: { userHistoryId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.userHistory).toBe(foundUserHistory);
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
