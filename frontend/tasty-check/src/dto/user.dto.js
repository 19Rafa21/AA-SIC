export class UserDTO {
  constructor({
    id = null,
    username = '',
    email = '',
    imageName = '',
    birthDate = '',
    country = '',
    contact = '',
    discriminator = 'User'
  } = {}) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.imageName = imageName;
    this.birthDate = birthDate;
    this.country = country;
    this.contact = contact;
    this.discriminator = discriminator;
  }

  static fromAPI(data) {
    return new UserDTO({
      id: data.id,
      username: data.username,
      email: data.email,
      imageName: data.imageName,
      birthDate: data.birthDate,
      country: data.country,
      contact: data.contact,
      discriminator: data.discriminator
    });
  }

  static toAPI(user) {
    return {
      username: user.username,
      imageName: user.imageName,
      birthDate: user.birthDate,
      country: user.country,
      contact: user.contact
    };
  }
}

export default UserDTO;
