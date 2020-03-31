import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AntecedentesPersonalesService } from 'app/entities/antecedentes-personales/antecedentes-personales.service';
import { IAntecedentesPersonales, AntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';

describe('Service Tests', () => {
  describe('AntecedentesPersonales Service', () => {
    let injector: TestBed;
    let service: AntecedentesPersonalesService;
    let httpMock: HttpTestingController;
    let elemDefault: IAntecedentesPersonales;
    let expectedResult: IAntecedentesPersonales | IAntecedentesPersonales[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AntecedentesPersonalesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AntecedentesPersonales(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AntecedentesPersonales', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AntecedentesPersonales()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AntecedentesPersonales', () => {
        const returnedFromService = Object.assign(
          {
            cirugias: 'BBBBBB',
            traumatismos: 'BBBBBB',
            transfusionSanguinea: 'BBBBBB',
            alergias: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AntecedentesPersonales', () => {
        const returnedFromService = Object.assign(
          {
            cirugias: 'BBBBBB',
            traumatismos: 'BBBBBB',
            transfusionSanguinea: 'BBBBBB',
            alergias: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AntecedentesPersonales', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
