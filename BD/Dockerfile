FROM postgres:17

ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=admin
ENV POSTGRES_DB=tastycheck

# Diretório de configuração do PostgreSQL dentro do container
# É aqui que vamos colocar os nossos ficheiros customizados
# Estes ficheiros vão sobrescrever os da imagem oficial
COPY custom-config/postgresql.conf /etc/postgresql/postgresql.conf
COPY custom-config/pg_hba.conf /etc/postgresql/pg_hba.conf

# Script SQL de inicialização
#COPY init.sql /docker-entrypoint-initdb.d/
RUN mkdir -p /etc/postgresql/conf.d

# Script de arranque para aplicar configs
# Isto garante que o PostgreSQL arranca com os nossos ficheiros
CMD ["postgres", "-c", "config_file=/etc/postgresql/postgresql.conf"]
