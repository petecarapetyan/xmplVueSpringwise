<template>
  <div>
    <h2 id="page-heading" data-cy="ScoreTypeHeading">
      <span v-text="$t('xmplVueSpringwiseApp.scoreType.home.title')" id="score-type-heading">Score Types</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('xmplVueSpringwiseApp.scoreType.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ScoreTypeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-score-type"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('xmplVueSpringwiseApp.scoreType.home.createLabel')"> Create a new Score Type </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && scoreTypes && scoreTypes.length === 0">
      <span v-text="$t('xmplVueSpringwiseApp.scoreType.home.notFound')">No scoreTypes found</span>
    </div>
    <div class="table-responsive" v-if="scoreTypes && scoreTypes.length > 0">
      <table class="table table-striped" aria-describedby="scoreTypes">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.scoreType.name')">Name</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="scoreType in scoreTypes" :key="scoreType.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ScoreTypeView', params: { scoreTypeId: scoreType.id } }">{{ scoreType.id }}</router-link>
            </td>
            <td>{{ scoreType.name }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ScoreTypeView', params: { scoreTypeId: scoreType.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ScoreTypeEdit', params: { scoreTypeId: scoreType.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(scoreType)"
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
        ><span id="xmplVueSpringwiseApp.scoreType.delete.question" data-cy="scoreTypeDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-scoreType-heading" v-text="$t('xmplVueSpringwiseApp.scoreType.delete.question', { id: removeId })">
          Are you sure you want to delete this Score Type?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-scoreType"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeScoreType()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./score-type.component.ts"></script>
