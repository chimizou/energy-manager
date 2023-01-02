# Energy Manager

## Context

Une partie du métier d'Agregio est de vendre de l'énergie sur plusieurs marchés, il y a 3 principaux marchés, celui de
la Réserve Primaire, la Réserve Secondaire et la Réserve Rapide. Sur chacun de ces marchés une offre est composée d'une
quantité d'énergie (en MW) qui sera livrée sur un "bloc" horaire (une journée de 24h pourrait contenir 8 blocs de 3
heures), et d'un prix plancher pour ce bloc horaire au-dessous duquel on ne vendra pas.

Les parcs producteurs d'électricité, de différents types (solaires, éoliens ou hydrauliques), sont capables de fournir
un certain nombre de MégaWatt pendant un bloc horaire. Pour permettre la traçabilité de la production électrique (
garantie d'origine), on doit pouvoir connaître le parc qui va produire l'électricité d'une offre.

Nous vous demandons d'implémenter les APIs permettant de créer une offre, de créer un parc, de lister les offres
proposées par Agregio pour chaque marché et d'obtenir la liste des parcs qui vendent sur un marché.