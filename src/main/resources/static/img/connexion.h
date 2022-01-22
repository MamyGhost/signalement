#ifndef CONNEXION_H
#define CONNEXION_H

class QSqlDatabase;

class Connexion
{
public:
    Connexion();
    void createConnection();
    void endConnection();
};

#endif // CONNEXION_H
