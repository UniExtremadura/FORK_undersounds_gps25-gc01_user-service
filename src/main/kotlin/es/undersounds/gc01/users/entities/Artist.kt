package es.undersounds.gc01.users.entities

import jakarta.persistence.*

@Entity
class Artist(
    @OneToOne
    @PrimaryKeyJoinColumn(name="id")
    var user: User,

    @Column(nullable = false)
    var artisticName: String,

    @Column(nullable = false)
    var  iban: String,
    
    @Column(nullable = false)
    var deleted: Boolean  = false
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    var id: Long? = null
}