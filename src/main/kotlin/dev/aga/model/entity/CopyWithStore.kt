package dev.aga.model.entity

import org.komapper.core.dsl.query.EntityStore

interface CopyWithStore<E: Any> {
  fun copyWithStore(store: EntityStore?): E
}
