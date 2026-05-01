import { TestBed } from '@angular/core/testing';

import { MasseSalarialeMois } from './masse-salariale-mois';

describe('MasseSalarialeMois', () => {
  let service: MasseSalarialeMois;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MasseSalarialeMois);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
