package com.kolisnichenko2828.randomusers.data.remote

import com.kolisnichenko2828.randomusers.data.local.UsersEntity

fun UsersDto.toUserEntity(): UsersEntity {
    return UsersEntity(
        id = this.id,
        username = this.username,
        password = this.password,
        email = this.email,
        name = this.name,
        firstName = this.firstName,
        lastName = this.lastName,
        fullName = this.fullName,
        prefix = this.prefix,
        suffix = this.suffix,
        phone = this.phone,
        cell = this.cell,
        address = this.address,
        streetAddress = this.streetAddress,
        city = this.city,
        state = this.state,
        postalCode = this.postalCode,
        country = this.country,
        latitude = this.latitude,
        longitude = this.longitude,
        timezone = this.timezone,
        dob = this.dob,
        age = this.age,
        gender = this.gender,
        job = this.job,
        company = this.company,
        companyEmail = this.companyEmail,
        ssn = this.ssn,
        creditCard = this.creditCard,
        creditCardProvider = this.creditCardProvider,
        iban = this.iban,
        ipv4 = this.ipv4,
        ipv6 = this.ipv6,
        macAddress = this.macAddress,
        userAgent = this.userAgent,
        url = this.url,
        domain = this.domain,
        picture = this.picture,
        avatar = this.avatar,
        uuid = this.uuid,
        md5 = this.md5,
        sha1 = this.sha1,
        sha256 = this.sha256,
        locale = this.locale
    )
}

fun List<UsersDto>.toUserEntities(): List<UsersEntity> {
    return this.map { it.toUserEntity() }
}