<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="xmplVueSpringwiseApp.frog.home.createOrEditLabel"
          data-cy="FrogCreateUpdateHeading"
          v-text="$t('xmplVueSpringwiseApp.frog.home.createOrEditLabel')"
        >
          Create or edit a Frog
        </h2>
        <div>
          <div class="form-group" v-if="frog.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="frog.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.frog.name')" for="frog-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="frog-name"
              data-cy="name"
              :class="{ valid: !$v.frog.name.$invalid, invalid: $v.frog.name.$invalid }"
              v-model="$v.frog.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.frog.age')" for="frog-age">Age</label>
            <input
              type="number"
              class="form-control"
              name="age"
              id="frog-age"
              data-cy="age"
              :class="{ valid: !$v.frog.age.$invalid, invalid: $v.frog.age.$invalid }"
              v-model.number="$v.frog.age.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.frog.species')" for="frog-species">Species</label>
            <input
              type="text"
              class="form-control"
              name="species"
              id="frog-species"
              data-cy="species"
              :class="{ valid: !$v.frog.species.$invalid, invalid: $v.frog.species.$invalid }"
              v-model="$v.frog.species.$model"
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
            :disabled="$v.frog.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./frog-update.component.ts"></script>
