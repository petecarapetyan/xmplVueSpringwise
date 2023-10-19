<template>
  <div>
    <h2 id="page-heading" data-cy="SpringProjectHeading">
      <span v-text="$t('xmplVueSpringwiseApp.springProject.home.title')" id="spring-project-heading">Spring Projects</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('xmplVueSpringwiseApp.springProject.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'SpringProjectCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-spring-project"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('xmplVueSpringwiseApp.springProject.home.createLabel')"> Create a new Spring Project </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && springProjects && springProjects.length === 0">
      <span v-text="$t('xmplVueSpringwiseApp.springProject.home.notFound')">No springProjects found</span>
    </div>
    <div class="table-responsive" v-if="springProjects && springProjects.length > 0">
      <table class="table table-striped" aria-describedby="springProjects">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.springProject.title')">Title</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.springProject.description')">Description</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.springProject.imagePath')">Image Path</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.springProject.url')">Url</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="springProject in springProjects" :key="springProject.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SpringProjectView', params: { springProjectId: springProject.id } }">{{
                springProject.id
              }}</router-link>
            </td>
            <td>{{ springProject.title }}</td>
            <td>{{ springProject.description }}</td>
            <td>{{ springProject.imagePath }}</td>
            <td>{{ springProject.url }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'SpringProjectView', params: { springProjectId: springProject.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'SpringProjectEdit', params: { springProjectId: springProject.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(springProject)"
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
          id="xmplVueSpringwiseApp.springProject.delete.question"
          data-cy="springProjectDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-springProject-heading" v-text="$t('xmplVueSpringwiseApp.springProject.delete.question', { id: removeId })">
          Are you sure you want to delete this Spring Project?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-springProject"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeSpringProject()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./spring-project.component.ts"></script>
