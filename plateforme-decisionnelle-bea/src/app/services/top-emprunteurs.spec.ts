import { TestBed } from '@angular/core/testing';

import { TopEmprunteurs } from './top-emprunteurs';

describe('TopEmprunteurs', () => {
  let service: TopEmprunteurs;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TopEmprunteurs);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
