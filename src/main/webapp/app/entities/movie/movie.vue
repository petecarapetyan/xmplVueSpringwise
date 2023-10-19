<template>
  <div>
    <h2 id="page-heading" data-cy="MovieHeading">
      <span v-text="$t('xmplVueSpringwiseApp.movie.home.title')" id="movie-heading">Movies</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('xmplVueSpringwiseApp.movie.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'MovieCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-movie"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('xmplVueSpringwiseApp.movie.home.createLabel')"> Create a new Movie </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && movies && movies.length === 0">
      <span v-text="$t('xmplVueSpringwiseApp.movie.home.notFound')">No movies found</span>
    </div>
    <div class="table-responsive" v-if="movies && movies.length > 0">
      <table class="table table-striped" aria-describedby="movies">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.movie.name')">Name</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.movie.yearOf')">Year Of</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.movie.genre')">Genre</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.movie.rating')">Rating</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="movie in movies" :key="movie.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MovieView', params: { movieId: movie.id } }">{{ movie.id }}</router-link>
            </td>
            <td>{{ movie.name }}</td>
            <td>{{ movie.yearOf }}</td>
            <td>{{ movie.genre }}</td>
            <td>{{ movie.rating }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MovieView', params: { movieId: movie.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MovieEdit', params: { movieId: movie.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(movie)"
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
        ><span id="xmplVueSpringwiseApp.movie.delete.question" data-cy="movieDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-movie-heading" v-text="$t('xmplVueSpringwiseApp.movie.delete.question', { id: removeId })">
          Are you sure you want to delete this Movie?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-movie"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeMovie()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./movie.component.ts"></script>
