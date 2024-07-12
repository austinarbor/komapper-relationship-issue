package dev.aga.model.entity

import org.komapper.annotation.KomapperAutoIncrement
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperIgnore
import org.komapper.annotation.KomapperManyToOne
import org.komapper.annotation.KomapperTable
import org.komapper.core.dsl.query.EntityStore

@KomapperTable("children")
@KomapperEntity
@KomapperManyToOne(targetEntity = Parent::class, navigator = "parent")
@KomapperManyToOne(targetEntity = Group::class, navigator = "group")
data class Child(
  @KomapperAutoIncrement @KomapperId val id: Long? = null,
  val parentId: Long,
  val groupId: Long? = null,
  @KomapperIgnore private val store: EntityStore? = null,
)
