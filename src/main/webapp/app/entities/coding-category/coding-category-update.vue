<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="xmplVueSpringwiseApp.codingCategory.home.createOrEditLabel"
          data-cy="CodingCategoryCreateUpdateHeading"
          v-text="$t('xmplVueSpringwiseApp.codingCategory.home.createOrEditLabel')"
        >
          Create or edit a CodingCategory
        </h2>
        <div>
          <div class="form-group" v-if="codingCategory.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="codingCategory.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.codingCategory.name')" for="coding-category-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="coding-category-name"
              data-cy="name"
              :class="{ valid: !$v.codingCategory.name.$invalid, invalid: $v.codingCategory.name.$invalid }"
              v-model="$v.codingCategory.name.$model"
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
            :disabled="$v.codingCategory.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./coding-category-update.component.ts"></script>
