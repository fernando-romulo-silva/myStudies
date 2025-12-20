#! /bin/bash

diretorio_backup="/home/fernando/Downloads/devops"
nome_arquivo="backup_$(date +%Y%m%d_%H%M%S).tar.gz"
tar -czf "$nome_arquivo" "$diretorio_backup"
echo "Backup concluído em $nome_arquivo"