<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="xmplVueSpringwiseApp.springProject.home.createOrEditLabel"
          data-cy="SpringProjectCreateUpdateHeading"
          v-text="$t('xmplVueSpringwiseApp.springProject.home.createOrEditLabel')"
        >
          Create or edit a SpringProject
        </h2>
        <div>
          <div class="form-group" v-if="springProject.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="springProject.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.springProject.title')" for="spring-project-title"
              >Title</label
            >
            <input
              type="text"
              class="form-control"
              name="title"
              id="spring-project-title"
              data-cy="title"
              :class="{ valid: !$v.springProject.title.$invalid, invalid: $v.springProject.title.$invalid }"
              v-model="$v.springProject.title.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.springProject.description')" for="spring-project-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="spring-project-description"
              data-cy="description"
              :class="{ valid: !$v.springProject.description.$invalid, invalid: $v.springProject.description.$invalid }"
              v-model="$v.springProject.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.springProject.imagePath')" for="spring-project-imagePath"
              >Image Path</label
            >
            <input
              type="text"
              class="form-control"
              name="imagePath"
              id="spring-project-imagePath"
              data-cy="imagePath"
              :class="{ valid: !$v.springProject.imagePath.$invalid, invalid: $v.springProject.imagePath.$invalid }"
              v-model="$v.springProject.imagePath.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.springProject.url')" for="spring-project-url">Url</label>
            <input
              type="text"
              class="form-control"
              name="url"
              id="spring-project-url"
              data-cy="url"
              :class="{ valid: !$v.springProject.url.$invalid, invalid: $v.springProject.url.$invalid }"
              v-model="$v.springProject.url.$model"
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
            :disabled="$v.springProject.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./spring-project-update.component.ts"></script>
