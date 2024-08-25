-- Password = 12345678
INSERT INTO users (name, email, password, role) VALUES
('Syllas', 'syllas@email.com', '$2a$10$tx8hMNFU8VcmRtmFVneEIeEsX2dLTsf1ydvalraBcaI6LEfK3B7PW', 'USER'),
('Braga', 'braga@email.com', '$2a$10$tx8hMNFU8VcmRtmFVneEIeEsX2dLTsf1ydvalraBcaI6LEfK3B7PW', 'USER');

INSERT INTO tasks (title, description, status, createdDate, endDate, user_id) VALUES
('Pagar as contas', 'Pagar as contas de água, luz e internet', 'DONE', '2024-08-20', '2024-08-25', 1),
('Comprar mantimentos', 'Ir ao mercado e comprar alimentos para a semana', 'TODO', '2024-08-21', NULL, 1),
('Estudar para prova', 'Estudar para a prova de matemática na próxima semana', 'DONE', '2024-08-18', '2024-08-23', 1),
('Limpar a casa', 'Fazer a limpeza geral da casa no sábado', 'TODO', '2024-08-19', NULL, 1),
('Renovar documentos', 'Ir ao DETRAN para renovar a carteira de motorista', 'CANCELED', '2024-08-15', NULL, 1),
('Planejar férias', 'Planejar viagem de férias para o final do ano', 'TODO', '2024-08-25', NULL, 2),
('Reunião com cliente', 'Reunião para discutir o projeto com o cliente', 'DONE', '2024-08-22', '2024-08-22', 2),
('Escrever relatório', 'Escrever o relatório anual da empresa', 'DONE', '2024-08-23', '2024-08-24', 2),
('Exercícios físicos', 'Ir à academia e praticar exercícios regularmente', 'TODO', '2024-08-21', NULL, 2),
('Organizar arquivos', 'Organizar os documentos e arquivos do escritório', 'CANCELED', '2024-08-20', NULL, 2);

INSERT INTO subtasks (createdDate, deadLineDate, endDate, task_id, description, title, status, priority) VALUES
('2024-08-20', '2024-08-24', '2024-08-24', 1, 'Pagar a conta de água', 'Pagar água', 'DONE', TRUE),
('2024-08-20', '2024-08-24', '2024-08-24', 1, 'Pagar a conta de luz', 'Pagar luz', 'DONE', TRUE),
('2024-08-21', '2024-08-25', NULL, 2, 'Comprar frutas e legumes', 'Comprar vegetais', 'TODO', FALSE),
('2024-08-21', '2024-08-25', NULL, 2, 'Comprar carnes', 'Comprar carne', 'TODO', FALSE),
('2024-08-19', '2024-08-23', '2024-08-23', 3, 'Revisar conteúdo de álgebra', 'Estudar álgebra', 'DONE', TRUE),
('2024-08-25', '2024-09-01', NULL, 6, 'Decidir destino da viagem', 'Escolher destino', 'TODO', FALSE),
('2024-08-22', '2024-08-22', '2024-08-22', 7, 'Discutir escopo do projeto', 'Definir escopo', 'DONE', TRUE),
('2024-08-23', '2024-08-24', '2024-08-24', 8, 'Revisar relatórios financeiros', 'Analisar finanças', 'DONE', FALSE),
('2024-08-21', '2024-08-26', NULL, 9, 'Fazer 30 minutos de esteira', 'Correr na esteira', 'TODO', FALSE),
('2024-08-20', '2024-08-24', NULL, 10, 'Organizar papéis do ano passado', 'Organizar papéis', 'CANCELED', FALSE);