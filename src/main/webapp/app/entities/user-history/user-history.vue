<template>
  <div>
    <h2 id="page-heading" data-cy="UserHistoryHeading">
      <span v-text="$t('xmplVueSpringwiseApp.userHistory.home.title')" id="user-history-heading">User Histories</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('xmplVueSpringwiseApp.userHistory.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'UserHistoryCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-user-history"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('xmplVueSpringwiseApp.userHistory.home.createLabel')"> Create a new User History </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && userHistories && userHistories.length === 0">
      <span v-text="$t('xmplVueSpringwiseApp.userHistory.home.notFound')">No userHistories found</span>
    </div>
    <div class="table-responsive" v-if="userHistories && userHistories.length > 0">
      <table class="table table-striped" aria-describedby="userHistories">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.userHistory.name')">Name</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.userHistory.issue')">Issue</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.userHistory.issueDate')">Issue Date</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="userHistory in userHistories" :key="userHistory.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'UserHistoryView', params: { userHistoryId: userHistory.id } }">{{ userHistory.id }}</router-link>
            </td>
            <td>{{ userHistory.name }}</td>
            <td>{{ userHistory.issue }}</td>
            <td>{{ userHistory.issueDate }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'UserHistoryView', params: { userHistoryId: userHistory.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'UserHistoryEdit', params: { userHistoryId: userHistory.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(userHistory)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="xmplVueSpringwiseApp.userHistory.delete.question"
          data-cy="userHistoryDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-userHistory-heading" v-text="$t('xmplVueSpringwiseApp.userHistory.delete.question', { id: removeId })">
          Are you sure you want to delete this User History?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-userHistory"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeUserHistory()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./user-history.component.ts"></script>
