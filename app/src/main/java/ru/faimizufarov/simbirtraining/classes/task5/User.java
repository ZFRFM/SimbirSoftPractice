package com.example.javacoretrainingpart1.classes.task5;

/*
      V

      Класс Абонент: Идентификационный номер, Фамилия, Имя, Отчество, Адрес,
      Номер кредитной карточки, Дебет, Кредит, Время междугородных и городских переговоров;
      Конструктор; Методы: установка значений атрибутов, получение значений атрибутов,
      вывод информации. Создать массив объектов данного класса.
      Вывести сведения относительно абонентов, у которых время городских переговоров
      превышает заданное.  Сведения относительно абонентов, которые пользовались
      междугородной связью. Список абонентов в алфавитном порядке.

*/

import androidx.annotation.NonNull;

class User {
    private long id;
    private String surname, name, lastName, address;
    private int creditCard, debet, credit, minOfInternal, minOfInternational;

    User(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(int creditCard) {
        this.creditCard = creditCard;
    }

    public int getDebet() {
        return debet;
    }

    public void setDebet(int debet) {
        this.debet = debet;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getMinOfInternal() {
        return minOfInternal;
    }

    public void setMinOfInternal(int minOfInternal) {
        this.minOfInternal = minOfInternal;
    }

    public int getMinOfInternational() {
        return minOfInternational;
    }

    public void setMinOfInternational(int minOfInternational) {
        this.minOfInternational = minOfInternational;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", creditCard=" + creditCard +
                ", debet=" + debet +
                ", credit=" + credit +
                ", minOfInternal=" + minOfInternal +
                ", minOfInternational=" + minOfInternational +
                '}';
    }

    void printUserInfo() {
        System.out.println(this);
    }
}