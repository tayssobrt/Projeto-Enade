-- Tabela de Cursos
CREATE TABLE curso
(
    id   BIGINT AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- Tabela de Papeis (roles)
-- MySQL não tem CREATE TYPE; o ENUM é declarado direto na coluna
CREATE TABLE roles
(
    id   BIGINT AUTO_INCREMENT,
    name ENUM('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER') NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uc_roles_name UNIQUE (name)
);

-- Tabela de Usuários
CREATE TABLE usuario
(
    id            BIGINT AUTO_INCREMENT,
    username      VARCHAR(20)  NOT NULL,
    email         VARCHAR(50)  NOT NULL,
    password      VARCHAR(120) NOT NULL,
    id_curso     BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT uc_users_username UNIQUE (username),
    CONSTRAINT uc_users_email UNIQUE (email),
    CONSTRAINT fk_users_course FOREIGN KEY (id_curso) REFERENCES curso (id)
);

-- Tabela de Tokens de Redefinição de Senha (password_reset_token)
CREATE TABLE password_reset_token
(
    id          BIGINT AUTO_INCREMENT,
    token       VARCHAR(255) NOT NULL,
    user_id     BIGINT       NOT NULL,
    expiry_date DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT uc_password_reset_token_user UNIQUE (user_id),
    CONSTRAINT fk_password_reset_token_user FOREIGN KEY (user_id) REFERENCES usuario (id)
);

-- Tabela de Associação Usuários e Papéis (user_roles)
CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES usuario (id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- Tabela de Questões (questao)
CREATE TABLE questao
(
    id_questao    INT AUTO_INCREMENT PRIMARY KEY,
    titulo        VARCHAR(150)  NOT NULL,
    enunciado     VARCHAR(2000) NOT NULL,
    tipo_questao  VARCHAR(100)  NOT NULL,
    area_questao  VARCHAR(100)  NOT NULL,
    possui_imagem BOOLEAN       NOT NULL,
    explicacao    VARCHAR(1000),
    img_url       VARCHAR(255)
);

-- Tabela de Alternativas (alternativa)
CREATE TABLE alternativa
(
    id_alternativa    INT AUTO_INCREMENT PRIMARY KEY,
    texto             VARCHAR(500) NOT NULL,
    is_correta        BOOLEAN,
    opcao_alternativa VARCHAR(10),
    id_questao        INT NOT NULL,
    CONSTRAINT fk_alternativa_questao FOREIGN KEY (id_questao) REFERENCES questao (id_questao)
);

CREATE TABLE IF NOT EXISTS simulado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    data_criacao DATE NOT NULL,
    quantidade_de_questoes INT,
    id_curso BIGINT,
    tipo_simulado ENUM('ORIGINAL', 'SIMULADO') NOT NULL,
    CONSTRAINT fk_simulado_curso FOREIGN KEY (id_curso) REFERENCES curso(id)
);

CREATE TABLE IF NOT EXISTS simulado_questao (
    id_simulado BIGINT NOT NULL,
    id_questao BIGINT NOT NULL,
    PRIMARY KEY (id_simulado, id_questao),
    CONSTRAINT fk_simuladoquestao_simulado FOREIGN KEY (id_simulado) REFERENCES simulado(id),
    CONSTRAINT fk_simuladoquestao_questao FOREIGN KEY (id_questao) REFERENCES questao(id_questao)
);


CREATE TABLE IF NOT EXISTS usuario_simulado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT,
    id_simulado BIGINT,
    nota DOUBLE,
    quantidade_acertos INT,
    quantidade_de_questoes INT,
    quantidade_de_respostas INT,
    data_conclusao DATE,
    finalizado BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_usuariosimulado_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    CONSTRAINT fk_usuariosimulado_simulado FOREIGN KEY (id_simulado) REFERENCES simulado(id)
);


CREATE TABLE IF NOT EXISTS usuario_questao (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   id_usuario_simulado BIGINT,
   id_questao BIGINT,
   is_acerto BOOLEAN NOT NULL,
   data_resposta DATE NOT NULL,
   id_alternativa BIGINT,
   CONSTRAINT fk_usuarioquestao_usuariosimulado FOREIGN KEY (id_usuario_simulado) REFERENCES usuario_simulado(id),
    CONSTRAINT fk_usuarioquestao_questao FOREIGN KEY (id_questao) REFERENCES questao(id_questao),
    CONSTRAINT fk_usuarioquestao_alternativa FOREIGN KEY (id_alternativa) REFERENCES alternativa(id_alternativa)
);