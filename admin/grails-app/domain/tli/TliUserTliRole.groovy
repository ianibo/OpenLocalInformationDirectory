package tli

import org.apache.commons.lang.builder.HashCodeBuilder

class TliUserTliRole implements Serializable {

	private static final long serialVersionUID = 1

	TliUser tliUser
	TliRole tliRole

	boolean equals(other) {
		if (!(other instanceof TliUserTliRole)) {
			return false
		}

		other.tliUser?.id == tliUser?.id &&
			other.tliRole?.id == tliRole?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (tliUser) builder.append(tliUser.id)
		if (tliRole) builder.append(tliRole.id)
		builder.toHashCode()
	}

	static TliUserTliRole get(long tliUserId, long tliRoleId) {
		TliUserTliRole.where {
			tliUser == TliUser.load(tliUserId) &&
			tliRole == TliRole.load(tliRoleId)
		}.get()
	}

	static TliUserTliRole create(TliUser tliUser, TliRole tliRole, boolean flush = false) {
		new TliUserTliRole(tliUser: tliUser, tliRole: tliRole).save(flush: flush, insert: true)
	}

	static boolean remove(TliUser u, TliRole r, boolean flush = false) {

		int rowCount = TliUserTliRole.where {
			tliUser == TliUser.load(u.id) &&
			tliRole == TliRole.load(r.id)
		}.deleteAll()

		rowCount > 0
	}

	static void removeAll(TliUser u) {
		TliUserTliRole.where {
			tliUser == TliUser.load(u.id)
		}.deleteAll()
	}

	static void removeAll(TliRole r) {
		TliUserTliRole.where {
			tliRole == TliRole.load(r.id)
		}.deleteAll()
	}

	static mapping = {
		id composite: ['tliRole', 'tliUser']
		version false
	}
}
