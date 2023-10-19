<template>
  <div>
    <h2 id="page-heading" data-cy="CodingCategoryHeading">
      <span v-text="$t('xmplVueSpringwiseApp.codingCategory.home.title')" id="coding-category-heading">Coding Categories</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('xmplVueSpringwiseApp.codingCategory.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CodingCategoryCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-coding-category"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('xmplVueSpringwiseApp.codingCategory.home.createLabel')"> Create a new Coding Category </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && codingCategories && codingCategories.length === 0">
      <span v-text="$t('xmplVueSpringwiseApp.codingCategory.home.notFound')">No codingCategories found</span>
    </div>
    <div class="table-responsive" v-if="codingCategories && codingCategories.length > 0">
      <table class="table table-striped" aria-describedby="codingCategories">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.codingCategory.name')">Name</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="codingCategory in codingCategories" :key="codingCategory.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CodingCategoryView', params: { codingCategoryId: codingCategory.id } }">{{
                codingCategory.id
              }}</router-link>
            </td>
            <td>{{ codingCategory.name }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'CodingCategoryView', params: { codingCategoryId: codingCategory.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'CodingCategoryEdit', params: { codingCategoryId: codingCategory.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(codingCategory)"
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
          id="xmplVueSpringwiseApp.codingCategory.delete.question"
          data-cy="codingCategoryDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-codingCategory-heading" v-text="$t('xmplVueSpringwiseApp.codingCategory.delete.question', { id: removeId })">
          Are you sure you want to delete this Coding Category?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-codingCategory"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCodingCategory()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./coding-category.component.ts"></script>
