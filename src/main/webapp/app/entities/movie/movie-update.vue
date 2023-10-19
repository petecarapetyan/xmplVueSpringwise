<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="xmplVueSpringwiseApp.movie.home.createOrEditLabel"
          data-cy="MovieCreateUpdateHeading"
          v-text="$t('xmplVueSpringwiseApp.movie.home.createOrEditLabel')"
        >
          Create or edit a Movie
        </h2>
        <div>
          <div class="form-group" v-if="movie.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="movie.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.movie.name')" for="movie-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="movie-name"
              data-cy="name"
              :class="{ valid: !$v.movie.name.$invalid, invalid: $v.movie.name.$invalid }"
              v-model="$v.movie.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.movie.yearOf')" for="movie-yearOf">Year Of</label>
            <input
              type="number"
              class="form-control"
              name="yearOf"
              id="movie-yearOf"
              data-cy="yearOf"
              :class="{ valid: !$v.movie.yearOf.$invalid, invalid: $v.movie.yearOf.$invalid }"
              v-model.number="$v.movie.yearOf.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.movie.genre')" for="movie-genre">Genre</label>
            <input
              type="text"
              class="form-control"
              name="genre"
              id="movie-genre"
              data-cy="genre"
              :class="{ valid: !$v.movie.genre.$invalid, invalid: $v.movie.genre.$invalid }"
              v-model="$v.movie.genre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.movie.rating')" for="movie-rating">Rating</label>
            <input
              type="text"
              class="form-control"
              name="rating"
              id="movie-rating"
              data-cy="rating"
              :class="{ valid: !$v.movie.rating.$invalid, invalid: $v.movie.rating.$invalid }"
              v-model="$v.movie.rating.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.movie.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./movie-update.component.ts"></script>
