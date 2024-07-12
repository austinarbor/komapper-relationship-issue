package dev.aga.model.entity

import org.komapper.annotation.KomapperAutoIncrement
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperIgnore
import org.komapper.annotation.KomapperOneToMany
import org.komapper.annotation.KomapperTable
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.query.EntityStore

@KomapperTable("parents")
@KomapperEntity
@KomapperOneToMany(targetEntity = Child::class, navigator = "children")
@KomapperOneToMany(targetEntity = Group::class, navigator = "groups")
data class Parent(
  @KomapperAutoIncrement @KomapperId val id: Long? = null,
  val name: String,
  @KomapperIgnore private val store: EntityStore? = null,
) {
  val groups: List<Group>? by lazy {
    store?.oneToManyById(Meta.parent, Meta.group)?.get(id)?.map { it.copy(store = store) }
  }
}
