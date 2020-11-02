var key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYHkrHT5NOyuZuKDvklwoTWcsjeKbnNuYpCfOk5PeA5blom4lKAe0OUIR9kzuAPjo3jeB3eSwFcFOZa58bTwXQHzAO/rMdVyA7IyWL3KXovnsKjuP/ooJUzDkGOLLXhWVseG5C5mwyoJ1JskQ4HRxTqBHMOsxmvaKWRqI3eWZYBwIDAQAB";

function wrapData(data) {
    data.password = encrypt(data.password + "." + data.verifyCode);
    return data;
}

function encrypt(payload) {
    var encrypt = new JSEncrypt();
    encrypt.setPublicKey(key);
    return encrypt.encrypt(payload);
}
