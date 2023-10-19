<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="xmplVueSpringwiseApp.student.home.createOrEditLabel"
          data-cy="StudentCreateUpdateHeading"
          v-text="$t('xmplVueSpringwiseApp.student.home.createOrEditLabel')"
        >
          Create or edit a Student
        </h2>
        <div>
          <div class="form-group" v-if="student.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="student.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.student.name')" for="student-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="student-name"
              data-cy="name"
              :class="{ valid: !$v.student.name.$invalid, invalid: $v.student.name.$invalid }"
              v-model="$v.student.name.$model"
              required
            />
            <div v-if="$v.student.name.$anyDirty && $v.student.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.student.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.student.initials')" for="student-initials">Initials</label>
            <input
              type="text"
              class="form-control"
              name="initials"
              id="student-initials"
              data-cy="initials"
              :class="{ valid: !$v.student.initials.$invalid, invalid: $v.student.initials.$invalid }"
              v-model="$v.student.initials.$model"
              required
            />
            <div v-if="$v.student.initials.$anyDirty && $v.student.initials.$invalid">
              <small class="form-text text-danger" v-if="!$v.student.initials.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
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
            :disabled="$v.student.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./student-update.component.ts"></script>
