import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AntecedentesFamiliaresService } from 'app/entities/antecedentes-familiares/antecedentes-familiares.service';
import { IAntecedentesFamiliares, AntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';

describe('Service Tests', () => {
  describe('AntecedentesFamiliares Service', () => {
    let injector: TestBed;
    let service: AntecedentesFamiliaresService;
    let httpMock: HttpTestingController;
    let elemDefault: IAntecedentesFamiliares;
    let expectedResult: IAntecedentesFamiliares | IAntecedentesFamiliares[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AntecedentesFamiliaresService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AntecedentesFamiliares(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AntecedentesFamiliares', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AntecedentesFamiliares()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AntecedentesFamiliares', () => {
        const returnedFromService = Object.assign(
          {
            madre: 'BBBBBB',
            padre: 'BBBBBB',
            abueloPaterno: 'BBBBBB',
            abueloMaterno: 'BBBBBB',
            abuelaPaterna: 'BBBBBB',
            abuelaMaterna: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AntecedentesFamiliares', () => {
        const returnedFromService = Object.assign(
          {
            madre: 'BBBBBB',
            padre: 'BBBBBB',
            abueloPaterno: 'BBBBBB',
            abueloMaterno: 'BBBBBB',
            abuelaPaterna: 'BBBBBB',
            abuelaMaterna: 'BBBBBB'
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

      it('should delete a AntecedentesFamiliares', () => {
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
