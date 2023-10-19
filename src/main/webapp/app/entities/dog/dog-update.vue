<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="xmplVueSpringwiseApp.dog.home.createOrEditLabel"
          data-cy="DogCreateUpdateHeading"
          v-text="$t('xmplVueSpringwiseApp.dog.home.createOrEditLabel')"
        >
          Create or edit a Dog
        </h2>
        <div>
          <div class="form-group" v-if="dog.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="dog.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.dog.name')" for="dog-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="dog-name"
              data-cy="name"
              :class="{ valid: !$v.dog.name.$invalid, invalid: $v.dog.name.$invalid }"
              v-model="$v.dog.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.dog.age')" for="dog-age">Age</label>
            <input
              type="number"
              class="form-control"
              name="age"
              id="dog-age"
              data-cy="age"
              :class="{ valid: !$v.dog.age.$invalid, invalid: $v.dog.age.$invalid }"
              v-model.number="$v.dog.age.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.dog.breed')" for="dog-breed">Breed</label>
            <input
              type="text"
              class="form-control"
              name="breed"
              id="dog-breed"
              data-cy="breed"
              :class="{ valid: !$v.dog.breed.$invalid, invalid: $v.dog.breed.$invalid }"
              v-model="$v.dog.breed.$model"
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
            :disabled="$v.dog.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./dog-update.component.ts"></script>
